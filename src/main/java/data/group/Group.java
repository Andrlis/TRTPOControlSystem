package data.group;

import org.apache.log4j.Logger;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class containing information abput group and list of subgroup
 */
@Entity
@Table(name = "groups")
public class Group {
    private static final Logger logger = Logger.getLogger(Group.class);
    @Id
    @Column(name = "id_group")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "group_number", length = 10)
    private String numberOfGroup;
    @OneToMany(mappedBy = "group")
    @LazyCollection(LazyCollectionOption.TRUE)
    private List<SubGroup> subGroupList;

    public Group() {
        this.subGroupList = new ArrayList<SubGroup>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumberOfGroup() {
        return numberOfGroup;
    }

    public void setNumberOfGroup(String numberOfGroup) {
        this.numberOfGroup = numberOfGroup;
    }

    public List<SubGroup> getSubGroupList() {
        return subGroupList;
    }

    public SubGroup getSubGroup(String number) {
        for (SubGroup subGroup : this.subGroupList) {
            if (subGroup.getSubGroupNumber().equals(number))
                return subGroup;
        }
        return null;
    }

    public void setSubGroupList(List<SubGroup> subGroupList) {
        this.subGroupList = subGroupList;
    }

    public void addSubGroup(SubGroup subGroup) {
        logger.info("Add subgroup(number " + subGroup.getSubGroupNumber() +") from group(" + this.getNumberOfGroup() + ").");
        if (!this.subGroupList.contains(subGroup))
            this.subGroupList.add(subGroup);
    }

    @Override
    public String toString() {
        return "Group{" +
                " numberOfGroup='" + numberOfGroup + '\'' +
                '}';
    }
}
