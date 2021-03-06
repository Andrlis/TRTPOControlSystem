package data.group;

import data.lab.IssuedLab;
import data.lecturer.Lecturer;
import data.Student;
import data.UniversityClass;
import org.apache.log4j.Logger;
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
@Table(name = "subgroups")
public class SubGroup {
    private static final Logger logger = Logger.getLogger(SubGroup.class);
    @Id
    @Column(name = "id_subgroup")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "subgroup_number", table = "subgroups", length = 5)
    private String subGroupNumber;
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "id_subgroup")
    private List<Student> studentsList;
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "id_subgroup")
    private List<IssuedLab> issuedLabsList;
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "id_subgroup")
    private List<UniversityClass> universityClassesList;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_group")
    private Group group;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_lecturer")
    private Lecturer lecturer;


    public SubGroup() {
        this.studentsList = new ArrayList<Student>();
        this.universityClassesList = new ArrayList<UniversityClass>();
        this.issuedLabsList = new ArrayList<IssuedLab>();
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

    public void addStudents(List<Student> studentsList) {
        for (Student student: studentsList) {
            student.setSubGroup(this);
            this.addStudent(student);
        }
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

    public IssuedLab getIssuedLabByNumber(String number) {
        IssuedLab issuedLab = null;
        for(IssuedLab lab: issuedLabsList){
            if(lab.getLabDescription().getNumberOfLab().equals(Integer.parseInt(number))){
                issuedLab = lab;
                break;
            }
        }

        return issuedLab;
    }


    public void addStudent(Student student) {
        logger.info("Add student(" + student.getFulName() + ") from subgroup(" + group.getNumberOfGroup() + ", " + subGroupNumber + ").");
        this.studentsList.add(student);
    }

    public void addIssuedLab(IssuedLab issuedLab) {
        logger.info("Add issue lab(" + issuedLab.getLabDescription().getNumberOfLab() + ") from subgroup(" + group.getNumberOfGroup() + ", " + subGroupNumber +").");
        this.issuedLabsList.add(issuedLab);
    }

    public void addUniversityClass(UniversityClass universityClass) {
        logger.info("Add university class(" + universityClass.getDate() + ") from subgroup(" + group.getNumberOfGroup() + ", " + subGroupNumber +").");
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

    @Override
    public String toString() {
        return "SubGroup{" +
                "id=" + id +
                ", subGroupNumber='" + subGroupNumber + '\'' +
                ", group=" + group +
                '}';
    }

    public boolean equals(SubGroup subGroup){
        return this.id.equals(subGroup.getId());
    }
}

