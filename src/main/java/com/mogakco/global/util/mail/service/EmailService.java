package com.mogakco.global.util.mail.service;

import com.mogakco.global.util.mail.model.EmailMessage;

@FunctionalInterface
public interface EmailService {
    void sendEmail(EmailMessage emailMessage);
}
