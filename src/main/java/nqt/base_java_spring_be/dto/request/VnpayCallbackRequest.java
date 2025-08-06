package nqt.base_java_spring_be.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import nqt.base_java_spring_be.dto.QrCodeItemPayment;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VnpayCallbackRequest {
    @JsonProperty("code")
    private String code;              // Mã lỗi trừ tiền khách hàng (Required, Max 10)

    @JsonProperty("message")
    private String message;           // Mô tả mã lỗi (Required, Max 100)

    @JsonProperty("msgType")
    private String msgType;           // Loại thanh toán (1 hoặc 2) (Required, Max 10)

    @JsonProperty("txnId")
    private String txnId;             // Mã đơn hàng / Số hóa đơn trong QRCode (Required, Max 20)

    @JsonProperty("qrTrace")
    private String qrTrace;           // Số trace giao dịch (Required, Max 10)

    @JsonProperty("bankCode")
    private String bankCode;          // Mã ngân hàng (Required, Max 10)

    @JsonProperty("mobile")
    private String mobile;            // Số điện thoại khách hàng (Optional, Max 20)

    @JsonProperty("accountNo")
    private String accountNo;         // Số tài khoản (Optional, Max 30)

    @JsonProperty("amount")
    private String amount;            // Số tiền thanh toán (Required, Max 13)

    @JsonProperty("payDate")
    private String payDate;           // Thời hạn thanh toán yyyyMMddHHmmss (Required, Max 14)

    @JsonProperty("merchantCode")
    private String merchantCode;      // Mã Merchant do VNPAY cấp (Required, Max 20)

    @JsonProperty("terminalId")
    private String terminalId;        // Mã terminal (Required, Max 8)

    // Các trường tùy chọn lấy từ bảng chung (nếu có)
    @JsonProperty("name")
    private String name;              // Tên người nhận hàng (Optional)

    @JsonProperty("phone")
    private String phone;             // SĐT nhận hàng (Optional)

    @JsonProperty("province_id")
    private String provinceId;        // ID tỉnh (Optional)

    @JsonProperty("district_id")
    private String districtId;        // ID quận/huyện (Optional)

    @JsonProperty("address")
    private String address;           // Địa chỉ (Optional)

    @JsonProperty("email")
    private String email;             // Email (Optional)

    @JsonProperty("ccy")
    private String ccy;               // Mã tiền tệ (Optional)

    @JsonProperty("addData")
    private List<QrCodeItemPayment> addData;
    /**
     * Danh sách chi tiết sản phẩm/dịch vụ (Optional)
     * QrCodeItemPayment chứa các trường:
     *   productId, amount, tipAndFee, ccy, qty, note :contentReference[oaicite:5]{index=5}
     */

    @JsonProperty("checksum")
    private String checksum;          // MD5 của chuỗi nối theo công thức (Required)
}
