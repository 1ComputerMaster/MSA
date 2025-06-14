import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.banking.adapter.in.web.RegisterBankAccountRequest;
import com.payment.banking.domain.RegisteredBankAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterBankAccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegisterBankAccount() throws Exception{
        //아래 인풋을 가지고
        RegisterBankAccountRequest request = new RegisterBankAccountRequest(
                "1", // membershipId
                "bankName", // bankName
                "bankAccountNumber", // bankAccountNumber
                true // linkedStatusIsValid
        );
        //post 요청을 보내면 성공
        RegisteredBankAccount registeredBankAccount = RegisteredBankAccount.generateRegisteredBankAccount(
                new RegisteredBankAccount.RegisteredBankAccountId("1"),
                new RegisteredBankAccount.MembershipId("1"),
                new RegisteredBankAccount.BankName("bankName"),
                new RegisteredBankAccount.BankAccountNumber("bankAccountNumber"),
                new RegisteredBankAccount.LinkedStatusIsValid(true)
        );
        //아래 테스트 코드를 통해서 코드 오류를 찾음
        mockMvc.perform(
                MockMvcRequestBuilders.post("/banking/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registeredBankAccount))
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(registeredBankAccount)));
    }
}
