package nqt.base_java_spring_be.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QrCodeItemPayment {
    @JsonProperty("productId")
    private String productId;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("tipAndFee")
    private String tipAndFee;

    @JsonProperty("ccy")
    private String ccy;

    @JsonProperty("qty")
    private String qty;

    @JsonProperty("note")
    private String note;
}
