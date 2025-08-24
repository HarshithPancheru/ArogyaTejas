package com.devmosaic.arogyatejas.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "notifications")
public class Notification {
    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID notificationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type = Type.SYSTEM;

    private boolean isRead = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Type {
        REMINDER, STATUS_UPDATE, SYSTEM
    }
}
