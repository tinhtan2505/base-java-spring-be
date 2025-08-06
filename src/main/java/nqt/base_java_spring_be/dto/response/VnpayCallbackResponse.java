package nqt.base_java_spring_be.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VnpayCallbackResponse {
    @JsonProperty("code")
    private String code;        // 00 = Merchant đã nhận thành công

    @JsonProperty("message")
    private String message;     // Mô tả kết quả (Required, Max 100)

    @JsonProperty("data")
    private Object data;        // Thông tin chi tiết (ví dụ { "txnId": "50141" }) (Optional)

    @JsonProperty("checksum")
    private String checksum;    // MD5(code + secretKey)

    public VnpayCallbackResponse(String code, String message, String checksum) {
        this.code = code;
        this.message = message;
        this.checksum = checksum;
    }
}
