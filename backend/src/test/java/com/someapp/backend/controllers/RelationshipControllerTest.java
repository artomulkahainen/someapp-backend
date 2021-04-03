package com.someapp.backend.controllers;

import com.someapp.backend.repositories.*;
import com.someapp.backend.util.TestData;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RelationshipControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostCommentRepository postCommentRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    private TestData testData = new TestData();

    @Before
    public void initializeTestData() throws Exception {
        ///testData = new TestData();
        if (testData.getUserId() == null) {
            testData.createTestData(userRepository, postRepository, postCommentRepository, postLikeRepository, relationshipRepository);
        }
    }
}
