package com.vasily_sokolov.nucacola.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterial {
    private int rawMaterialId;
    private String rawMaterialName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RawMaterial that = (RawMaterial) o;
        return rawMaterialId == that.rawMaterialId && Objects.equals(rawMaterialName, that.rawMaterialName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawMaterialId, rawMaterialName);
    }

    @Override
    public String toString() {
        return "RawMaterial{" +
                "rawMaterialId=" + rawMaterialId +
                ", rawMaterialName='" + rawMaterialName + '\'' +
                '}';
    }
}
