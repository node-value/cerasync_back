package node_value.projects.cerasync_back.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import node_value.projects.cerasync_back.service.EventService;
import node_value.projects.cerasync_back.util.dto.reqDTO.EventDTO;
import node_value.projects.cerasync_back.util.dto.respDTO.AllEventsResponse;
import node_value.projects.cerasync_back.util.exceptions.EventAlreadyExistsException;
import node_value.projects.cerasync_back.util.exceptions.EventNotFoundException;
import node_value.projects.cerasync_back.util.exceptions.UserNotFoundException;

@RestController
@RequestMapping("/api/events")
@CrossOrigin
public class EventController {
    
    @Autowired private EventService eventService;

    @GetMapping("/public/get_all_events")
    public ResponseEntity<AllEventsResponse> getAllEvents() { //add filter on past events 
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/public/get_event_by_id")
    public ResponseEntity<?> getEventById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(eventService.getEventById(id));
        } catch (EventNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get_events_by_owner")
    public ResponseEntity<?> getEventsByOwner(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok(eventService.getEventByUserId(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add_event")
    public ResponseEntity<?> addEvent(@Validated @RequestBody EventDTO dto, Principal principal) {
        try {
            return ResponseEntity.ok(eventService.addEvent(dto, principal.getName()));
        } catch (EventAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update_event")
    public ResponseEntity<?> updateEvent(@Validated @RequestBody EventDTO dto, Principal principal) {
        try {
            eventService.updateEvent(dto);
            return ResponseEntity.ok("Update complete");
        } catch (EventNotFoundException | NullPointerException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete_event")
    public ResponseEntity<?> deleteEvent(@RequestParam Long id, Principal principal) {
        eventService.deleteEventById(id);
        return ResponseEntity.ok("Delete complete");
    }
}
