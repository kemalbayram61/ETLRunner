package tr.com.kml.enums;

import lombok.Getter;

/**
 * Scriptin sisteme girişinden itibaren geçtiği aşamaları temsil eder.
 */
@Getter
public enum ScriptStatus {

    /** Kullanıcı scripti yazıyor, henüz onaya sunulmadı. */
    DRAFT("Taslak"),

    /** Onay bekliyor, onaycı ekranına düştü. */
    PENDING_APPROVAL("Onay Bekliyor"),

    /** Onaycı tarafından reddedildi, kullanıcı revize etmeli. */
    REJECTED("Reddedildi"),

    /** Onaylandı ve kuyruğa gönderilmeye hazır. */
    APPROVED("Onaylandı"),

    /** Kuyruğa (RabbitMQ) iletildi, worker tarafından alınmayı bekliyor. */
    QUEUED("Kuyrukta"),

    /** Worker scripti şu an hedef veritabanında çalıştırıyor. */
    RUNNING("Çalışıyor"),

    /** İşlem başarıyla tamamlandı. */
    COMPLETED("Tamamlandı"),

    /** Çalıştırma sırasında bir hata oluştu. */
    FAILED("Hata Alındı");

    private final String description;

    ScriptStatus(String description) {
        this.description = description;
    }
}
