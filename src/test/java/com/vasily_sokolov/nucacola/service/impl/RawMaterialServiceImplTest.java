package com.vasily_sokolov.nucacola.service.impl;

import com.vasily_sokolov.nucacola.dto.RawMaterialDto;
import com.vasily_sokolov.nucacola.entity.RawMaterial;
import com.vasily_sokolov.nucacola.exception.exceptions.ListException;
import com.vasily_sokolov.nucacola.exception.exceptions.RawMaterialNotFoundException;
import com.vasily_sokolov.nucacola.mapper.RawMaterialMapper;
import com.vasily_sokolov.nucacola.repository.RawMaterialRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class RawMaterialServiceImplTest {

    @InjectMocks
    private RawMaterialServiceImpl rawMaterialService;

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @Mock
    private RawMaterialMapper rawMaterialMapper;

    private final String uuid = "7d2000c1-8111-420c-a42a-e4eca5b50090";

    private List<RawMaterial> rawMaterialList;
    private List<RawMaterialDto> rawMaterialDtoList;

    @BeforeEach
    public void init() {
        rawMaterialList = List.of(new RawMaterial());
        rawMaterialDtoList = List.of(new RawMaterialDto());
    }

    //1.---------------------------------
    // findRawMaterialById()---------------------------------------------
    @Test
    void findByIdPositiveTest() {
        Mockito.when(rawMaterialRepository.findById(UUID.fromString(uuid)))
                .thenReturn(Optional.of(new RawMaterial()));
        Assertions.assertEquals((new RawMaterial()), rawMaterialService.findById(uuid));
        Mockito.verify(rawMaterialRepository).findById(UUID.fromString(uuid));
    }

    @Test
    void findByIdRawMaterialNotFoundExceptionTest() {
        Assertions.assertThrows(RawMaterialNotFoundException.class, () -> rawMaterialService.findById(uuid));
    }

    //2.------------------------------------------getRawMaterialsByName()---------------------------------------------
    @Test
    void getRawMaterialByNamePositiveTest() {
        String name = "s";
        Mockito.when(rawMaterialRepository.getRawMaterialsByName(name))
                .thenReturn(rawMaterialList);
        Mockito.when(rawMaterialMapper.rawMaterialToRawMaterialDto(rawMaterialList))
                .thenReturn(rawMaterialDtoList);
        Assertions.assertEquals(1, rawMaterialService.getRawMaterialsByName(name).size());
        Mockito.verify(rawMaterialRepository).getRawMaterialsByName(name);
        Mockito.verify(rawMaterialMapper).rawMaterialToRawMaterialDto(rawMaterialList);
    }

    @Test
    void getRawMaterialsByNameListExceptionTest() {
        String name = "s";
        Assertions.assertThrows(ListException.class, () -> rawMaterialService.getRawMaterialsByName(name).size());

    }

    //3.------------------------------------------getAllRawMaterials()---------------------------------------------
    @Test
    void getAllRawMaterialsPositiveTest() {
        Mockito.when(rawMaterialRepository.findAll())
                .thenReturn(rawMaterialList);
        Mockito.when(rawMaterialMapper.rawMaterialToRawMaterialDto(rawMaterialList))
                .thenReturn(rawMaterialDtoList);
        Assertions.assertEquals(1, rawMaterialService.getAllRawMaterials().size());
        Mockito.verify(rawMaterialRepository).findAll();
        Mockito.verify(rawMaterialMapper).rawMaterialToRawMaterialDto(rawMaterialList);
    }

    @Test
    void getAllRawMaterialsListExceptionTest() {
        Assertions.assertThrows(ListException.class, () -> rawMaterialService.getAllRawMaterials().size());
    }

}