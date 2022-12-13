package com.example.Concert_Backend.services;

import com.example.Concert_Backend.models.Attendee;
import com.example.Concert_Backend.models.Concert;
import com.example.Concert_Backend.repositories.AttendeeRepository;
import com.example.Concert_Backend.repositories.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class AttendeeService {
    @Autowired
    AttendeeRepository attendeeRepository;

    @Autowired
    ConcertRepository concertRepository;

    public List<Attendee> getAllAttendees() {
        return attendeeRepository.findAll();
    }

    public Attendee getAttendeeById(Long id) {
        return attendeeRepository.findById(id).get();
    }

    public Attendee addNewAttendee(Attendee attendee) {
        attendeeRepository.save(attendee);
        return attendee;
    }

    public Attendee updateAttendee(Long id, Attendee attendee) {
        Attendee attendeeUpdate = attendeeRepository.findById(id).get();
        attendeeUpdate.setName(attendee.getName());
        attendeeUpdate.setEmailAddress(attendee.getEmailAddress());
        attendeeUpdate.setPhoneNumber(attendee.getPhoneNumber());
        attendeeRepository.save(attendeeUpdate);
        return attendeeUpdate;
    }

    public void deleteAttendee(Long id){
// taken from 'removeAttendeeFromConcert'
        Attendee foundAttendee = attendeeRepository.findById(id).get();
        for (Concert concert : foundAttendee.getConcerts()){
            concert.removeAttendee(foundAttendee);
            if (foundAttendee.equals(null)){
                break;
            } else{
                concertRepository.save(concert);
                attendeeRepository.deleteById(id);
                continue;
            }
        }
    }
}
