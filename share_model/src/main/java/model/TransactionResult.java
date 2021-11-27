package model;

import enums.OperationStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResult {
    private OperationStatus resultCode;
    private String message;

}
