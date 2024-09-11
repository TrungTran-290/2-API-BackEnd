package com.example.demo.models;

public enum XepLoai {
    Gioi("gioi"), Kha("kha"), TrungBinh("trung binh"), Yeu("yeu");
    private final String xepLoai;
    XepLoai(String xepLoai) {
        this.xepLoai = xepLoai;
    }

    public String getXepLoai() {
        return xepLoai;
    }
    public static XepLoai fromString(String xepLoai) {
        for (XepLoai xepLoai1 : XepLoai.values()) {
            if (xepLoai1.getXepLoai().equals(xepLoai)) {
                return xepLoai1;
            }
        }
        throw new IllegalArgumentException("Invalid XepLoai: " + xepLoai);
    }
}
