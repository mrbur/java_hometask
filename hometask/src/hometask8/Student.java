package hometask8;

import java.util.Map;

class Student {
    private String name;
    private Map<String, Integer> grades;

    public Student(String name, Map<String, Integer> grades) {
        this.name = name;
        this.grades = grades;
    }

    public Map<String, Integer> getGrades() {
        return grades;
    }


    //добавил от себя, для удобства распечатки
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", grades=" + grades +
                '}';
    }

    //не знаю можно ли его было добавлять потому это будет как альтернатива
    public String getName() {
        return name;
    }
}