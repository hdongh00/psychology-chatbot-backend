package chat.psychology_chatbot.repository;

import chat.psychology_chatbot.domain.PsychologyData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PsychologyDataRepository extends JpaRepository<PsychologyData, Long> {
    Optional<PsychologyData> findBySymptom(String symptom);
}
