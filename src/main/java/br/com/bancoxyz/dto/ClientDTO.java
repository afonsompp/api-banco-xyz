package br.com.bancoxyz.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.validator.constraints.br.CPF;

import br.com.bancoxyz.model.Client;
import br.com.bancoxyz.validation.constraint.CompleteNameConstraint;
import br.com.bancoxyz.validation.constraint.MinDateConstraint;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Client")
public class ClientDTO {
    @NotBlank(message = "{name.not.blank}")
    @Size(min = 7, max = 50, message = "{name.size}")
    @CompleteNameConstraint(message = "{name.pattern}")
    @ApiModelProperty(notes = "${client.model.name}",
    position = 1, example = "Bob Brown", required = true)
    private String name;

    @NotBlank(message = "{email.not.blank}")
    @Email(message = "{email.invalid}")
    @ApiModelProperty(notes = "${client.model.email}",
    position = 2, example = "exemple@exemple.com", required = true)
    private String email;

    @NotBlank(message = "{cpf.not.blank}")
    @CPF(message = "{cpf.invalid}")
    @ApiModelProperty(notes = "${client.model.email}",
    position = 3, example = "146.674.500-29", required = true)
    private String cpf;

    @NotNull(message = "{date.not.null}")
    @MinDateConstraint(message = "{date.custom.min.date}", minAge = 18)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    @ApiModelProperty(notes = "${client.model.date}",
    position = 4, example = "31/12/2000", required = true)
    private LocalDate dateOfBirth;

    public Client parseToClient() {
        return new Client(name, email, cpf, dateOfBirth);
    }
}
