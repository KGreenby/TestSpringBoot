package com.testspringboot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookReqFormDTO {

    private Long id;

    @NotBlank(message = "필수항목입니다.")
    private String title;

    @NotBlank(message = "필수항목입니다.")
    private String author;

    @NotBlank(message = "필수항목입니다.")
    private String isbn;

    private String genre;
}
