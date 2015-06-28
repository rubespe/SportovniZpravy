package wa2.Entity;

import org.json.JSONObject;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Petr on 15.6.2015.
 */

@Entity
@Table(name = "zapas")
public class Zapas {
    protected int id;
    protected String name;
    protected String date;
    protected Set<Sport> categories = new HashSet<Sport>();
    private Set<Udalost> messages = new HashSet<Udalost>();

    public Zapas() {
    }

    public Zapas(String date, String name) {
        this.name = name;
        this.date = date;
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    @JoinTable(name = "sport_zapas", joinColumns = {
            @JoinColumn(name = "zapas_id", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "sport_id",
                    nullable = false) })
    public Set<Sport> getCategories() {
        return categories;
    }

    public void setCategories(Set<Sport> categories) {
        this.categories = categories;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "zapas", cascade = CascadeType.ALL)
    public Set<Udalost> getMessages() {
        return messages;
    }

    public void setMessages(Set<Udalost> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +

                '}';
    }

    public JSONObject returnJSON(){
        JSONObject o = new JSONObject();
        o.put("id",id);
        o.put("name",name);
        o.put("date",date);
        return o;
    }
}


