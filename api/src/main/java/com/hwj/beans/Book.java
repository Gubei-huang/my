package com.hwj.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {
    private String isbn;
    private String bookName;
    private String author;
    private float price;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date publishTime;
    private String cover;
}
