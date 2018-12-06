package hr.meteor.ru.meteorjob.ui.beans;

public class Profession {
    private int professionId;
    private int imgId;
    private String name;

    public Profession() {
    }

    public Profession(int professionId, int imgId, String name) {
        this.professionId = professionId;
        this.imgId = imgId;
        this.name = name;
    }

    public int getProfessionId() {
        return professionId;
    }

    public void setProfessionId(int professionId) {
        this.professionId = professionId;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
