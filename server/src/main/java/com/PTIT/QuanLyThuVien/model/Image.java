package com.PTIT.QuanLyThuVien.model;

import lombok.*;

import javax.persistence.*;

@Table(name = "tbl_image")
@Data
@NoArgsConstructor
@Embeddable
public class Image {

    private String name;

    public Image(String name){
        this.name = name;
    }
}
