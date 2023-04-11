package com.lio.BlogApi.services.user.account;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lio.BlogApi.models.dtos.custom.EmailTemplate;
import com.lio.BlogApi.models.dtos.request.RegisterRequestDTO;
import com.lio.BlogApi.models.dtos.response.ApiResponse;
import com.lio.BlogApi.models.dtos.response.RegisterResponseDTO;
import com.lio.BlogApi.models.entities.Account;
import com.lio.BlogApi.models.enums.AccountStatus;
import com.lio.BlogApi.models.enums.MailSubject;
import com.lio.BlogApi.models.enums.MailTemplate;
import com.lio.BlogApi.models.enums.Message;
import com.lio.BlogApi.models.enums.Prefix;
import com.lio.BlogApi.models.enums.ViewId;
import com.lio.BlogApi.repositories.account.AccountRepository;
import com.lio.BlogApi.services.common.email.EmailService;
import com.lio.BlogApi.services.user.accountCode.AccountCodeService;
import com.lio.BlogApi.utils.ErrorMapUtil;
import com.lio.BlogApi.utils.GeneratorUtil;
import com.lio.BlogApi.utils.LinkUtil;
import com.lio.BlogApi.utils.ResponseUtil;
import com.lio.BlogApi.utils.TemplateUtil;
import com.lio.BlogApi.utils.TextUtil;

@Service("accountServiceForUser")
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AccountCodeService accountCodeService;

    public AccountServiceImpl(
            AccountRepository accountRepo,
            PasswordEncoder passwordEncoder,
            EmailService emailService,
            AccountCodeService accountCodeService) {
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.accountCodeService = accountCodeService;
    }

    @Override
    @Transactional
    public RegisterResponseDTO createAccount(RegisterRequestDTO registerRequestDTO)
            throws IOException, MessagingException {
        Account account = this.getRegisterAccountEntitiy(registerRequestDTO);
        account = this.accountRepo.save(account);

        if (account != null) {
            /*
             * generate code to verify account back from email
             */
            String verificationCode = this.accountCodeService.generateVerificationCode(account);
            Map<String, String> mappings = new HashMap<>();
            mappings.put("name", account.getUsername());
            mappings.put("link", LinkUtil.getVerificationLink(account.getEmail(), verificationCode));
            /*
             * getting email template for verification
             */
            String htmlTemplate = TemplateUtil.getEmailTemplate(MailTemplate.EMAIL_VERIFICATION.value());

            EmailTemplate emailTemplate = EmailTemplate.builder()
                    .createdDate(new Date())
                    .mailTo(account.getEmail())
                    .subject(MailSubject.EMAIL_VERIFICATION.msg())
                    .content(TextUtil.bindString(htmlTemplate, mappings))
                    .build();

            this.emailService.sendEmail(emailTemplate);

            return this.getRegisterResponse(account);
        }

        return null;
    }

    @Override
    public boolean isAccountEnabled(String email) {
        Optional<Account> foundAccount = this.accountRepo.findByEmail(email);
        return foundAccount.isPresent() && foundAccount.get().isActive();
    }

    @Override
    public boolean isAccountLocked(String email) {
        Optional<Account> foundAccount = this.accountRepo.findByEmail(email);
        return foundAccount.isPresent() && foundAccount.get().isLocked();
    }

    @Override
    public boolean isDuplicateEmail(String email) {
        Optional<Account> foundAccount = this.accountRepo.findByEmail(email);
        return foundAccount.isPresent();
    }

    @Override
    public ApiResponse<?> validateAccountRegistration(RegisterRequestDTO registerRequestDTO) {
        if (this.isDuplicateEmail(registerRequestDTO.getEmail())
                && this.isAccountEnabled(registerRequestDTO.getEmail())) {
            return ResponseUtil.errorResponse(
                    HttpStatus.BAD_GATEWAY,
                    HttpStatus.BAD_REQUEST.value(),
                    Message.DUPLICATE_EMAIL.value(),
                    ErrorMapUtil.getErrorMapFromValue(Message.DUPLICATE_EMAIL.value()));
        }

        if (this.isDuplicateEmail(registerRequestDTO.getEmail())
                && !this.isAccountEnabled(registerRequestDTO.getEmail())) {
            return ResponseUtil.errorResponse(
                    HttpStatus.BAD_GATEWAY,
                    HttpStatus.BAD_REQUEST.value(),
                    Message.ACCOUNT_NEED_VERIFICATION.value(),
                    ErrorMapUtil.getErrorMapFromValue(Message.ACCOUNT_NEED_VERIFICATION.value()));
        }

        if (this.isAccountLocked(registerRequestDTO.getEmail())) {
            return ResponseUtil.errorResponse(
                    HttpStatus.BAD_GATEWAY,
                    HttpStatus.BAD_REQUEST.value(),
                    Message.ACCOUNT_LOCKED.value(),
                    ErrorMapUtil.getErrorMapFromValue(Message.ACCOUNT_LOCKED.value()));
        }

        return null;
    }

    @Override
    public ApiResponse<?> validateRegisteredVerification(String email, String verificationCode) {
        Optional<Account> savedAccount = Optional.empty();
        if (email != null)
            savedAccount = this.accountRepo.findByEmail(email);
        if (email == null ||
                verificationCode == null ||
                savedAccount.isEmpty() ||
                verificationCode.length() != (ViewId.CODE.bound() + Prefix.CODE.value().length())) {
            return this.getValidateRegisteredVerificationResponse();
        }
        return null;
    }

    @Override
    @Transactional
    public ApiResponse<?> verifyRegisteredAccount(String email, String verificationCode) {
        Account savedAccount = this.accountRepo.findByEmail(email).get();

        if (this.accountCodeService.verifyAccountCode(savedAccount.getViewId(), verificationCode)) {
            savedAccount.setAccountStatus(AccountStatus.VERIFED.value());
            savedAccount.setActive(true);
            savedAccount.setUpdatedDate(LocalDateTime.now());

            this.accountRepo.save(savedAccount);

            return ResponseUtil.response(
                    HttpStatus.OK,
                    HttpStatus.OK.value(),
                    Message.ACCOUNT_VERIFY_SUCCESS.value(),
                    null);
        }

        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", Message.ACCOUNT_VERIFY_FAIL.value());

        return ResponseUtil.errorResponse(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                Message.ACCOUNT_VERIFY_FAIL.value(),
                errorMap);
    }

    /*
     * From here is just for method of object creation related with account
     */
    private Account getRegisterAccountEntitiy(RegisterRequestDTO registerRequestDTO) {
        Account account = Account.builder()
                .viewId(GeneratorUtil.generateId(Prefix.USER.value(), ViewId.ACCOUNT.bound()))
                .username(registerRequestDTO.getUsername())
                .email(registerRequestDTO.getEmail())
                .password(this.passwordEncoder.encode(registerRequestDTO.getPassword()))
                .location(registerRequestDTO.getLocation())
                .createdDate(LocalDateTime.now())
                .isActive(false)
                .isDelete(false)
                .isLocked(false)
                .accountStatus(AccountStatus.NOT_YET_VERIFIED.value())
                .build();
        return account;
    }

    private RegisterResponseDTO getRegisterResponse(Account account) {
        return RegisterResponseDTO.builder()
                .accountId(account.getViewId())
                .username(account.getUsername())
                .email(account.getEmail())
                .createdDate(LocalDateTime.now())
                .phone(account.getPhone())
                .location(account.getLocation())
                .build();
    }

    private ApiResponse<?> getValidateRegisteredVerificationResponse() {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", Message.INVALID_REQUEST.value());

        return ResponseUtil.errorResponse(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                Message.INVALID_REQUEST.value(),
                errorMap);
    }

    /*
     * Here is end for method of object creation related with account
     */

}
