package Exercises.Project3;

import Components.*;
import DataObjects.DataString;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Monitor {
    public static void main(String [] args) {
        PetriNet pn = new PetriNet();
        pn.PetriNetName = "Main Petri";
        pn.NetworkPort = 1081;

        DataString in = new DataString();
        in.SetName("in");
        pn.PlaceList.add(in);

        DataString p0 = new DataString();
        p0.SetName("p0");
        p0.SetValue("signal");
        pn.PlaceList.add(p0);

        DataString p1 = new DataString();
        p1.SetName("p1");
        pn.PlaceList.add(p1);


        // T0 ---------------------------------------------------
        PetriTransition t0 = new PetriTransition(pn);
        t0.TransitionName = "t0";
        t0.InputPlaceName.add("p0");
        t0.InputPlaceName.add("in");

        Condition t0Ct1 = new Condition(t0, "p0", TransitionCondition.NotNull);
        Condition t0Ct2 = new Condition(t0, "in", TransitionCondition.NotNull);
        t0Ct1.SetNextCondition(LogicConnector.AND, t0Ct2);

        GuardMapping grdt0 = new GuardMapping();
        grdt0.condition = t0Ct1;
        grdt0.Activations.add(new Activation(t0, "in", TransitionOperation.Move,"p1"));
        grdt0.Activations.add(new Activation(t0,"p0",TransitionOperation.Copy,"p0"));
        t0.GuardMappingList.add(grdt0);

        t0.Delay = 0;
        pn.Transitions.add(t0);


        pn.Delay = 1000;
        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);
    }
}
