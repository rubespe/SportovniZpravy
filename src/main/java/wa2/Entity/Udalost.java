package wa2.Entity;

import org.hibernate.annotations.ForeignKey;
import org.json.JSONObject;

import javax.persistence.*;

/**
 * Created by Petr on 18.6.2015.
 */
@Entity
@Table(name = "udalost")
public class Udalost {
    private int id;
    private String text;
    private String date;
    private Zapas zapas;

    public Udalost() {
    }

    public Udalost(String date, String text, Zapas zapas) {
        this.text = text;
        this.date = date;
        this.zapas = zapas;
    }

    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "zapas_id", nullable = false)
    @ForeignKey(name="FK_EVENTMSG")
    public Zapas getZapas() {
        return zapas;
    }

    public void setZapas(Zapas zapas) {
        this.zapas = zapas;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +

                '}';
    }

    public JSONObject returnJSON(){
        JSONObject o = new JSONObject();
        o.put("id",id);
        o.put("text",text);
        o.put("date",date);
        return o;
    }
}


