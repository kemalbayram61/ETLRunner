package tr.com.kml.dto.queue;

import tr.com.kml.enums.DatabaseType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record ScriptMessage(
        UUID messageId,           // Mesajın benzersiz kimliği (idempotency kontrolü için)
        Long scriptId,            // Veritabanındaki script tablosu ID'si
        String scriptContent,     // Çalıştırılacak olan asıl kod (SQL veya NoSQL komutu)
        Long targetDbId,          // Hedef veritabanı bağlantı bilgilerinin ID'si
        DatabaseType dbType,      // Hedef veritabanı türü (Enum)
        String approvedBy,        // Onayı veren kullanıcının adı
        LocalDateTime approvedAt  // Onaylanma zamanı
) implements Serializable {

    /**
     * Mesaj oluşturulurken varsayılan değerleri atayan bir yardımcı constructor.
     */
    public ScriptMessage {
        if (messageId == null) {
            messageId = UUID.randomUUID();
        }
        if (approvedAt == null) {
            approvedAt = LocalDateTime.now();
        }
    }
}