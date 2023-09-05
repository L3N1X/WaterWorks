package hr.algebra.waterworks.shared.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptFilterRequest {
    private LocalDate dateFrom;
    private LocalDate dateTo;

}
