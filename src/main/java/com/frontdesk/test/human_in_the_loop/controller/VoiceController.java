package com.frontdesk.test.human_in_the_loop.controller;

import com.frontdesk.test.human_in_the_loop.service.AgentService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/voice")
public class VoiceController {

    private final AgentService agent;

    public VoiceController(AgentService agent) {
        this.agent = agent;
    }

    // Pretend LiveKit sends: { "callerId":"+15551234567", "transcript":"Do you do hair coloring?" }
    @PostMapping("/transcript")
    public Map<String, String> handleTranscript(@RequestBody Map<String, String> payload) {
        String transcript = payload.getOrDefault("transcript", "");
        String reply = agent.handleCustomerQuestion(transcript); // reuse same logic
        // respond as { "reply": "AI: ..." } so a LiveKit bot could speak it back
        return Map.of("reply", reply);
    }
}
