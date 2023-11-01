package Exercises.Project1;

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

public class Ex1Client {
	public static void main(String[] args) {
		PetriNet pn = new PetriNet();
		pn.PetriNetName = "ClientPetri";
		pn.NetworkPort = 1081;

		DataFloat p0 = new DataFloat();
		p0.SetName("p0");
		p0.SetValue(1.0f);
		pn.PlaceList.add(p0);

		DataFloat p1 = new DataFloat();
		p1.SetName("p1");
		pn.PlaceList.add(p1);

		DataTransfer p3 = new DataTransfer();
		p3.SetName("p3");
		p3.Value = new TransferOperation("localhost", "1082", "p1");
		pn.PlaceList.add(p3);

		DataFloat p4 = new DataFloat();
		p4.SetName("p4");
		pn.PlaceList.add(p4);

		DataFloat p5 = new DataFloat();
		p5.SetName("p5");
		pn.PlaceList.add(p5);

		DataFloat p6 = new DataFloat();
		p6.SetName("p6");
		pn.PlaceList.add(p6);

		// T1
		PetriTransition t1 = new PetriTransition(pn);
		t1.TransitionName = "T1";
		t1.InputPlaceName.add("p0");
		t1.InputPlaceName.add("p1");

		Condition T0Ct1 = new Condition(t1, "p1", TransitionCondition.NotNull);
		Condition T0Ct2 = new Condition(t1, "p0", TransitionCondition.NotNull);
		T0Ct1.SetNextCondition(LogicConnector.AND, T0Ct2);

		GuardMapping grdT11 = new GuardMapping();
		grdT11.condition = T0Ct1;

		grdT11.Activations.add(new Activation(t1, "p1", TransitionOperation.SendOverNetwork, "p3"));
		grdT11.Activations.add(new Activation(t1, "p0", TransitionOperation.Move, "p4"));

		t1.GuardMappingList.add(grdT11);
		t1.Delay = 0;
		pn.Transitions.add(t1);


		// T2
		PetriTransition t2 = new PetriTransition(pn);
		t2.TransitionName = "T2";
		t2.InputPlaceName.add("p4");
		t2.InputPlaceName.add("p5");

		Condition T1Ct1 = new Condition(t2, "p4", TransitionCondition.NotNull);
		Condition T1Ct2 = new Condition(t2, "p5", TransitionCondition.NotNull);

		GuardMapping grdT12 = new GuardMapping();
		grdT12.condition = T1Ct1;
		grdT12.condition = T1Ct2;

		grdT12.Activations.add(new Activation(t2, "p5", TransitionOperation.Move, "p0"));
		grdT12.Activations.add(new Activation(t2, "p5", TransitionOperation.Move, "p6"));

		t2.GuardMappingList.add(grdT12);
		t2.Delay = 0;
		pn.Transitions.add(t2);

		PetriNetWindow frame = new PetriNetWindow(false);
		frame.petriNet = pn;
		frame.setVisible(true);
	}
}