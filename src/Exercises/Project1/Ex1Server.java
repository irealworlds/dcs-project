package Exercises.Project1;

import java.util.ArrayList;

import Components.Activation;
import Components.Condition;
import Components.GuardMapping;
import Components.PetriNet;
import Components.PetriNetWindow;
import Components.PetriTransition;
import DataObjects.DataFloat;
import DataObjects.DataTransfer;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Ex1Server {

    public static void main(String[] args) {
        PetriNet pn = new PetriNet();
        pn.PetriNetName = "ServerPetri";
        pn.NetworkPort = 1082;

        DataFloat p0 = new DataFloat();
        p0.SetName("p0");
        p0.SetValue(1.0f);
        pn.PlaceList.add(p0);

        DataFloat p1 = new DataFloat();
        p1.SetName("p1");
        pn.PlaceList.add(p1);

        DataFloat constantValue1 = new DataFloat();
        constantValue1.SetName("constantValue1");
        constantValue1.SetValue(0.01f);
        pn.ConstantPlaceList.add(constantValue1);

        DataTransfer p3 = new DataTransfer();
        p3.SetName("p3");
        p3.Value = new TransferOperation("localhost", "1081", "p5");
        pn.PlaceList.add(p3);

        DataFloat p2 = new DataFloat();
        p2.SetName("p2");
        pn.PlaceList.add(p2);


        // T1
        PetriTransition t1 = new PetriTransition(pn);
        t1.TransitionName = "t1";
        t1.InputPlaceName.add("p0");
        t1.InputPlaceName.add("p1");

        Condition T0Ct1 = new Condition(t1, "p1", TransitionCondition.NotNull);
        Condition T0Ct2 = new Condition(t1, "p0", TransitionCondition.NotNull);
        T0Ct1.SetNextCondition(LogicConnector.AND, T0Ct2);

        GuardMapping grdT11 = new GuardMapping();
        grdT11.condition = T0Ct1;

        ArrayList<String> lstInput = new ArrayList<String>();
        lstInput.add("p1");
        lstInput.add("constantValue1");

        grdT11.Activations.add(new Activation(t1, lstInput , TransitionOperation.Prod, "p2"));

        t1.GuardMappingList.add(grdT11);
        t1.Delay = 0;
        pn.Transitions.add(t1);


        // T2
        PetriTransition t2 = new PetriTransition(pn);
        t2.TransitionName = "T2";
        t2.InputPlaceName.add("p2");

        Condition T1Ct1 = new Condition(t2, "p2", TransitionCondition.NotNull);

        GuardMapping grdT12 = new GuardMapping();
        grdT12.condition = T1Ct1;
        grdT12.Activations.add(new Activation(t2, "p2", TransitionOperation.SendOverNetwork, "p3"));
        grdT12.Activations.add(new Activation(t2, "p2", TransitionOperation.Move, "p0"));

        t2.GuardMappingList.add(grdT12);
        t2.Delay = 0;
        pn.Transitions.add(t2);

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);
    }
}