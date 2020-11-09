package net.ellections.services;

import java.util.Arrays;
import java.util.List;
import net.ellections.entities.Candidate;
import net.ellections.reporitories.CandidateRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CandidateServiceTests {

    @Mock
    private CandidateRepository candidateRepository;

    @InjectMocks
    private CandidateService candidateService;

    @Test
    public void getAllCandidates_withNullUser_Test(){

        List<Candidate> candidateList;


        Candidate candidate1 = Candidate.builder().id(1L).firstName("Juan").lastName("Perez").user(null).build();
        Candidate candidate2 = Candidate.builder().id(2L).firstName("Pedro").lastName("Gonzalez").user(null).build();
        Candidate candidate3 = Candidate.builder().id(3L).firstName("Pablo").lastName("Fernandez").user(null).build();
        Candidate candidate4 = Candidate.builder().id(4L).firstName("Ruben").lastName("Garcia").user(null).build();
        Candidate candidate5 = Candidate.builder().id(5L).firstName("Maria").lastName("Mendez").user(null).build();

        candidateList = Arrays.asList(candidate1, candidate2, candidate3, candidate4, candidate5);

        when(candidateRepository.findAll()).thenReturn(candidateList);
        Assert.assertEquals(5, candidateService.getCandidates().size());
        Assert.assertEquals(candidateList, candidateService.getCandidates());
    }
}
