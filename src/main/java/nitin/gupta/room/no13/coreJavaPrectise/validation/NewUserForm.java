package nitin.gupta.room.no13.coreJavaPrectise.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "verifyPassword",
                message = "Passwords do not match!"
        ),
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "verifyEmail",
                message = "Email addresses do not match!"
        )
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserForm {
    private String email;
    private String verifyEmail;
    private String password;
    private String verifyPassword;

}
