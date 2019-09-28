package com.project.pontusgoaltracker.models;

import java.util.ArrayList;

public abstract class GoalType{

		//note : dont try to make objects from this class

        //todo : allow users to create custom Goal types:
        //todo : let each Goal type have customisable properties like colour;


		final public static String NULL = "";
        final public static String GENERAL = "General";
        final public static String RELIGIOUS ="Religious";
        final public static String FINANCIAL = "Financial";
        final public static String HEALTH ="Health";
        final public static String RELATIONSHIP = "Relationship";
        final public static String FAMILY ="Family";
        final public static String OCCUPATIONAL = "Occupational";
        final public static String ACADEMIC ="Academic";
        final public static String PERSONAL ="Personal";

        final public static String[] allGoalTypes={GENERAL,RELIGIOUS,FINANCIAL,HEALTH,
        RELATIONSHIP,FAMILY,OCCUPATIONAL,ACADEMIC,PERSONAL,NULL};




    }