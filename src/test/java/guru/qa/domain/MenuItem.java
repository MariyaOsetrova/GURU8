package guru.qa.domain;

public enum MenuItem {
// еречисление каких-то вещей, множества которое известно и не будет изменяться
    SEARCH("Поиск"),
    IMAGES("Картинки"),
    VIDEO("Видео");

    private String desc;

    MenuItem(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
