package com.liubo.domain.model.valobj.constant;

import com.liubo.domain.model.valobj.AiClientAdvisorVO;
import com.liubo.domain.service.armory.factory.element.RagAnswerAdvisor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AiClientAdvisorTypeEnum {

    CHAT_MEMORY("ChatMemory", "上下文记忆（内存模式）") {
        @Override
        public Advisor createAdvisor(AiClientAdvisorVO aiClientAdvisorVO, VectorStore vectorStore) {
            AiClientAdvisorVO.ChatMemory chatMemory = aiClientAdvisorVO.getChatMemory();
            return PromptChatMemoryAdvisor
                    .builder(MessageWindowChatMemory
                            .builder()
                            .maxMessages(chatMemory.getMaxMessages())
                            .build())
                    .build();
        }
    },

    RAG_ANSWER("RagAnswer", "知识库") {
        @Override
        public Advisor createAdvisor(AiClientAdvisorVO aiClientAdvisorVO, VectorStore vectorStore) {
            AiClientAdvisorVO.RagAnswer ragAnswer = aiClientAdvisorVO.getRagAnswer();
            return new RagAnswerAdvisor(vectorStore, SearchRequest
                    .builder()
                    .topK(ragAnswer.getTopK())
                    .filterExpression(ragAnswer.getFilterExpression())
                    .build());
        }
    };


    private String code;

    private String info;


    private static final Map<String, AiClientAdvisorTypeEnum> CODE_MAP = new HashMap<>();


    static {
        for (AiClientAdvisorTypeEnum advisorTypeEnum : values()) {
            CODE_MAP.put(advisorTypeEnum.getCode(), advisorTypeEnum);
        }
    }
    public abstract Advisor createAdvisor(AiClientAdvisorVO aiClientAdvisorVO, VectorStore vectorStore);

    public static AiClientAdvisorTypeEnum getByCode(String code) {
        AiClientAdvisorTypeEnum enumVO = CODE_MAP.get(code);
        if (enumVO == null) {
            throw new RuntimeException("err! advisorType " + code + " not exist!");
        }
        return enumVO;
    }
}
