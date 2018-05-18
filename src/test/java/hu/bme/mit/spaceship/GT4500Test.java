package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class GT4500Test {

  private GT4500 ship;
  TorpedoStore mockerdPrimaryTorpedoStore;
  TorpedoStore mockerdSecondaryTorpedoStore;

  @Before
  public void init(){
    mockerdPrimaryTorpedoStore = mock(TorpedoStore.class);
    mockerdSecondaryTorpedoStore = mock(TorpedoStore.class);
    this.ship = new GT4500(mockerdPrimaryTorpedoStore, mockerdSecondaryTorpedoStore);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockerdPrimaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    //assertEquals(true, result);
    verify(mockerdPrimaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockerdPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockerdSecondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    //assertEquals(true, result);
    verify(mockerdPrimaryTorpedoStore,atMost(1)).fire(1);
    verify(mockerdSecondaryTorpedoStore,atMost(1)).fire(1);

  }

  @Test
  public void fireTorpedo_Single_PrimaryNotEmpty_SecondaryNotEmpty_Success(){

    when(mockerdPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockerdSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockerdPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockerdSecondaryTorpedoStore.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true ,result);
  }

  @Test
  public void fireTorpedo_Single_PrimaryEmpty_SecondaryNotEmpty_Success(){

    when(mockerdPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockerdSecondaryTorpedoStore.isEmpty()).thenReturn(false);

    when(mockerdSecondaryTorpedoStore.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true ,result);
  }

  @Test
  public void fireTorpedo_Single_PrimaryEmpty_SecondaryEmpty_Success(){

    when(mockerdPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockerdSecondaryTorpedoStore.isEmpty()).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(false ,result);
  }

  @Test
  public void fireTorpedo_All_PrimaryEmpty_SecondaryNotEmpty_Success(){

    when(mockerdPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockerdSecondaryTorpedoStore.isEmpty()).thenReturn(false);

    when(mockerdSecondaryTorpedoStore.fire(1)).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    assertEquals(false ,result);
  }

  @Test
  public void fireTorpedo_All_PrimaryEmpty_SecondaryEmpty_Success(){
    when(mockerdPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockerdSecondaryTorpedoStore.isEmpty()).thenReturn(true);

    when(mockerdPrimaryTorpedoStore.fire(1)).thenReturn(false);
    when(mockerdSecondaryTorpedoStore.fire(1)).thenReturn(false);
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    assertEquals(false ,result);
  }

  @Test
  public  void fireTorpedo(){

    when(mockerdPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockerdSecondaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockerdSecondaryTorpedoStore.isEmpty()).thenReturn(true);

    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockerdPrimaryTorpedoStore,times(2)).fire(1);
    verify(mockerdSecondaryTorpedoStore,times(0)).fire(1);

  }

  @Test
  public void fireAll(){
    when(mockerdPrimaryTorpedoStore.fire(1)).thenReturn(false);
    when(mockerdSecondaryTorpedoStore.fire(1)).thenReturn(true);
    assertEquals(true, ship.fireTorpedo(FiringMode.ALL));

  }

}
