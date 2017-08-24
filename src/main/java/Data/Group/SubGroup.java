package Data.Group;

import Data.Lab.IssuedLab;
import Data.Lecturer.Lecturer;
import Data.Student;
import Data.UniversityClass;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * class containing information about subgroup and list of classes, list of laboratory work
 */
@Entity
@Table(name = "groups_subgroups")
@SecondaryTable(name = "subgroups", pkJoinColumns =
@PrimaryKeyJoinColumn(name = "id_group_subgroup", referencedColumnName = "id_group_subgroup"))
@Proxy(lazy = false)
public class SubGroup {
    @Id
    @Column(name = "id_group_subgroup")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "subgroup_number", table = "subgroups", length = 5)
    private String subGroupNumber;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_group_subgroup")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Student> studentsList;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_group_subgroup")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<UniversityClass> universityClassesList;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_group_subgroup")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<IssuedLab> issuedLabsList;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_group")
    private Group group;
    @ManyToOne()
    @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "id_lecturer")
    private Lecturer lecturer;


    public SubGroup() {
        this.studentsList = new ArrayList<Student>();
        this.universityClassesList = new ArrayList<UniversityClass>();
        this.issuedLabsList = new ArrayList<IssuedLab>();
    }

    public SubGroup(Integer id, String subGroupNumber, List<Student> studentsList, List<UniversityClass> universityClassesList, List<IssuedLab> issuedLabsList, Group group, Lecturer lecturer) {
        this.id = id;
        this.subGroupNumber = subGroupNumber;
        this.studentsList = studentsList;
        this.universityClassesList = universityClassesList;
        this.issuedLabsList = issuedLabsList;
        this.group = group;
        this.lecturer = lecturer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubGroupNumber() {
        return subGroupNumber;
    }

    public void setSubGroupNumber(String subGroupNumber) {
        this.subGroupNumber = subGroupNumber;
    }

    public List<Student> getStudentsList() {
        return studentsList;
    }

    public void setStudentsList(List<Student> studentsList) {
        this.studentsList = studentsList;
    }

    public List<UniversityClass> getUniversityClassesList() {
        return universityClassesList;
    }

    public void setUniversityClassesList(List<UniversityClass> universityClassesList) {
        this.universityClassesList = universityClassesList;
    }

    public List<IssuedLab> getIssuedLabsList() {
        return issuedLabsList;
    }

    public void setIssuedLabsList(List<IssuedLab> issuedLabsList) {
        this.issuedLabsList = issuedLabsList;
    }

    public void addStudent(Student student) {
        this.studentsList.add(student);
    }

    public void addIssuedLab(IssuedLab issuedLab) {
        this.issuedLabsList.add(issuedLab);
    }

    public void addUniversityClass(UniversityClass universityClass) {
        this.universityClassesList.add(universityClass);
    }

    public UniversityClass getNextUniversityClass(UniversityClass universityClass) {
        for (UniversityClass currentClass : universityClassesList) {
            if (currentClass.getDate().getTime() > universityClass.getDate().getTime()) {
                return currentClass;
            }
        }
        return null;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }
}

