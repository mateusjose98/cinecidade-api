package br.com.cinecidade.cinecidade_api.application.movie;

import java.util.List;
import java.util.function.Function;

public class PageResult<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public PageResult(List<T> content, int page, int size, long totalElements, int totalPages) {

        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public <R> PageResult<R> map(final Function<T, R> mapper) {
        final List<R> aNewList = this.content.stream()
                .map(mapper)
                .toList();

        return new PageResult<>(aNewList, getPage(), getSize(), getTotalElements(), getTotalPages());
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }


}
