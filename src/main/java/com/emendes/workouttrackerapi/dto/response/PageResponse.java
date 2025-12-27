package com.emendes.workouttrackerapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Objeto para paginação.
 */
@AllArgsConstructor
@Builder
@Getter
public class PageResponse<T> {

  private List<T> content;
  private long totalElements;
  private long totalPages;
  private int currentPage;
  private int pageSize;

}
