package com.cat.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="scan_event")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScanEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double longitude;
    private Double latitude;
    private LocalDateTime scanTime;

    @ManyToOne
    @JoinColumn(name = "info_id")
    private Info info;

    private Double accuracy;

    @Enumerated(EnumType.STRING)
    private ScanEventType eventType;
}
