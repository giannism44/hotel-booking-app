package gr.aueb.cf.hotelapp.core.enums;

public enum PredefinedRoom {
    R101("101", RoomType.SINGLE, 70.0),
    R102("102", RoomType.SINGLE, 70.0),
    R103("103", RoomType.SINGLE, 70.0),
    R104("104", RoomType.SINGLE, 70.0),
    R105("105", RoomType.SINGLE, 70.0),
    R106("106", RoomType.SINGLE, 70.0),
    R107("107", RoomType.SINGLE, 70.0),
    R108("108", RoomType.SINGLE, 70.0),
    R109("109", RoomType.SINGLE, 70.0),
    R110("110", RoomType.SINGLE, 70.0),

    R201("201", RoomType.SINGLE, 70.0),
    R202("202", RoomType.SINGLE, 70.0),
    R203("203", RoomType.SINGLE, 70.0),
    R204("204", RoomType.SINGLE, 70.0),
    R205("205", RoomType.SINGLE, 70.0),
    R206("206", RoomType.SINGLE, 70.0),
    R207("207", RoomType.SINGLE, 70.0),
    R208("208", RoomType.SINGLE, 70.0),
    R209("209", RoomType.SINGLE, 70.0),
    R210("210", RoomType.SINGLE, 70.0),

    R301("301", RoomType.SINGLE, 70.0),
    R302("302", RoomType.SINGLE, 70.0),
    R303("303", RoomType.SINGLE, 70.0),
    R304("304", RoomType.SINGLE, 70.0),
    R305("305", RoomType.SINGLE, 70.0),
    R306("306", RoomType.SINGLE, 70.0),
    R307("307", RoomType.SINGLE, 70.0),
    R308("308", RoomType.SINGLE, 70.0),
    R309("309", RoomType.SINGLE, 70.0),
    R310("310", RoomType.SINGLE, 70.0),

    R401("401", RoomType.DOUBLE, 100.0),
    R402("402", RoomType.DOUBLE, 100.0),
    R403("403", RoomType.DOUBLE, 100.0),
    R404("404", RoomType.DOUBLE, 100.0),
    R405("405", RoomType.DOUBLE, 100.0),
    R406("406", RoomType.DOUBLE, 100.0),
    R407("407", RoomType.DOUBLE, 100.0),
    R408("408", RoomType.DOUBLE, 100.0),
    R409("409", RoomType.DOUBLE, 100.0),
    R410("410", RoomType.DOUBLE, 100.0),

    R501("501", RoomType.SUITE, 150.0),
    R502("502", RoomType.SUITE, 150.0),
    R503("503", RoomType.SUITE, 150.0),
    R504("504", RoomType.SUITE, 150.0),
    R505("505", RoomType.SUITE, 150.0),
    R506("506", RoomType.SUITE, 150.0),
    R507("507", RoomType.SUITE, 150.0),
    R508("508", RoomType.SUITE, 150.0),
    R509("509", RoomType.SUITE, 150.0),
    R510("510", RoomType.SUITE, 150.0);

    private final String roomNumber;
    private final RoomType roomType;
    private final Double price;

    PredefinedRoom(String roomNumber, RoomType roomType, Double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public Double getPrice() {
        return price;
    }
}
