package managers;

import java.util.ArrayList;

import objects.Event;

public class EventManager {

    private ArrayList<Event> events = new ArrayList<Event>();

    public void fireEvent(Event event) {
        events.add(event);
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void clearEvent() {
        events.clear();
    }


}
