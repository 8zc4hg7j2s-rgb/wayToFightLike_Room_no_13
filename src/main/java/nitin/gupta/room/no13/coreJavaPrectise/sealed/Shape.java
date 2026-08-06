package nitin.gupta.room.no13.coreJavaPrectise.sealed;

public sealed interface Shape permits Circle,Triangle,Rectangle{
    public void display();
}
