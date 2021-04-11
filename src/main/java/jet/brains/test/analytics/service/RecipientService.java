package jet.brains.test.analytics.service;

import jet.brains.test.analytics.entity.Recipient;
import jet.brains.test.analytics.repository.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipientService {
    @Autowired
    RecipientRepository recipientRepository;

    public Recipient saveRecipient(String address) {
        if (!recipientRepository.existsByAddress(address)) {
            Recipient recipient = new Recipient();
            recipient.setAddress(address);
            recipientRepository.save(recipient);
        }
        return recipientRepository.getRecipientByAddress(address);
    }

    public List<Recipient> transferFromStringListToRecipientList(List<String> list) {
        List<Recipient> recipients = new ArrayList<>();
        for (String address : list) {
            Recipient recipient = saveRecipient(address);
            recipients.add(recipient);
        }
        return recipients;
    }
}
