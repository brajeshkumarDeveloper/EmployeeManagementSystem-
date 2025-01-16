package com.ems.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "this will be user id"
)
public class EmployeeDto {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;



	

}
