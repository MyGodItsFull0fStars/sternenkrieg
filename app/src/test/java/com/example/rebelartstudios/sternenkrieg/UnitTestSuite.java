package com.example.rebelartstudios.sternenkrieg;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by christianbauer on 19/06/2017.
 */

@RunWith(Suite.class)

@Suite.SuiteClasses({
        DiceTests.class,
        NetworkingUnitTests.class,
        NetworkStatsTests.class,
        NetworkUtilitiesTests.class,
        PlayerFieldLogicTests.class,
        ShipLogicTests.class
})

public class UnitTestSuite {
}
