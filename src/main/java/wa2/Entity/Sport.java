package wa2.Entity;

/**
 * Created by Petr on 15.6.2015.
 */

import org.json.JSONObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sport")
public class Sport {

    private int id;
    private String name;
    private Set<Zapas> zapasy = new HashSet<Zapas>();

    public Sport() {
    }

    public Sport(String name) {
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

    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "sport_zapas", joinColumns = {
            @JoinColumn(name = "sport_id", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "zapas_id",
                    nullable = false) })
    public Set<Zapas> getZapasy() {
        return zapasy;
    }

    public void setZapasy(Set<Zapas> zapasy) {
        this.zapasy = zapasy;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +

                '}';
    }

    public JSONObject returnJSON(){
        JSONObject o = new JSONObject();
        o.put("id",id);
        o.put("name",name);

    return o;
    }
}
