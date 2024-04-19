package com.velotronix;

// pyramid class, that corresponds to the information in the json file
public class Pyramid {

  protected Integer id;
  protected String name;
  protected String[] contributors;

  // constructor
  public Pyramid(
    Integer pyramidId,
    String pyramidName,
    String[] pyramidContributors
  ) {
    id = pyramidId;
    name = pyramidName;
    contributors = pyramidContributors;
  }

  public String getName() {
    return name;
  }

  public String[] getContributors() {
  return contributors;
  }

  public void print() {
    System.out.printf("Pyramid %s\n", name);
    System.out.printf("\tID: %d\n", id);   
  }
}
