package com.pluralsight.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BatchRequest implements Serializable{
   private static final long serialVersionUID = -2970075210625130774L;
   
   private List<String> idsToUpdate = new ArrayList<>();

   public List<String> getIdsToUpdate() {
      return idsToUpdate;
   }

   public void setIdsToUpdate(List<String> idsToUpdate) {
      this.idsToUpdate = idsToUpdate;
   }

}
