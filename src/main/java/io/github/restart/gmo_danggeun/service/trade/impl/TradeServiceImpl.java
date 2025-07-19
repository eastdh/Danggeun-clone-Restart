package io.github.restart.gmo_danggeun.service.trade.impl;

import io.github.restart.gmo_danggeun.dto.trade.TradeDto;
import io.github.restart.gmo_danggeun.dto.trade.TradeEditDto;
import io.github.restart.gmo_danggeun.entity.Category;
import io.github.restart.gmo_danggeun.entity.Trade;
import io.github.restart.gmo_danggeun.entity.User;
import io.github.restart.gmo_danggeun.entity.readonly.TradeDetail;
import io.github.restart.gmo_danggeun.entity.readonly.TradeImageList;
import io.github.restart.gmo_danggeun.entity.readonly.TradeList;
import io.github.restart.gmo_danggeun.repository.TradeRepository;
import io.github.restart.gmo_danggeun.repository.readonly.TradeDetailRepository;
import io.github.restart.gmo_danggeun.repository.readonly.TradeImageListRepository;
import io.github.restart.gmo_danggeun.repository.readonly.TradeListRepository;
import io.github.restart.gmo_danggeun.service.trade.TradeService;
import io.github.restart.gmo_danggeun.util.DateUtil;
import io.github.restart.gmo_danggeun.util.SecurityUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TradeServiceImpl implements TradeService {

  private final TradeRepository tradeRepository;
  private final TradeListRepository tradeListRepository;
  private final TradeDetailRepository tradeDetailRepository;
  private final TradeImageListRepository tradeImageListRepository;

  public TradeServiceImpl(TradeRepository tradeRepository, TradeListRepository tradeListRepository,
      TradeDetailRepository tradeDetailRepository,
      TradeImageListRepository tradeImageListRepository) {
    this.tradeRepository = tradeRepository;
    this.tradeListRepository = tradeListRepository;
    this.tradeDetailRepository = tradeDetailRepository;
    this.tradeImageListRepository = tradeImageListRepository;
  }

  @Override
  public Page<TradeList> searchTrades(String keyword, String location, String category,
      Integer priceLowLimit, Integer priceHighLimit,
      String status, Pageable pageable) {
    Page<TradeList> pages =
    tradeListRepository.findAllByFilters(keyword, location, category, priceLowLimit, priceHighLimit, status, pageable);

    List<TradeList> list = new ArrayList<>();
    pages.get().forEach(trade -> {
      String updateTerm = DateUtil.getMaxDateTerm(trade.getUpdateTerm());
      String bumpUpdateTerm = DateUtil.getMaxDateTerm(trade.getBumpUpdateTerm());

      TradeList newTrade = new TradeList(
          trade.getTradeId(), trade.getTitle(),
          trade.getPrice(), trade.getStatus(),
          trade.isHidden(), trade.getCreatedAt(),
          trade.getUpdatedAt(), trade.getBumpUpdatedAt(),
          updateTerm, bumpUpdateTerm,
          trade.getUserId(), trade.getLocation(),
          trade.getCategoryId(), trade.getCategoryName(),
          trade.getImgUrl());
      list.add(newTrade);
    });

    return new PageImpl<>(list, pageable, list.size());
  }

  @Override
  public Optional<Trade> findById(Long id) {
    Optional<Trade> optionalTrade = tradeRepository.findById(id);
    if (!optionalTrade.isEmpty()) {
      Trade trade = optionalTrade.get();
      trade.setTitle(
          SecurityUtil.htmlUnescape(trade.getTitle())
      );
      trade.setDescription(
          SecurityUtil.htmlUnescape(trade.getDescription())
      );
      if (trade.getPreferredLocation() != null)
        trade.setPreferredLocation(
            SecurityUtil.htmlUnescape(trade.getPreferredLocation())
        );
    }
    return optionalTrade;
  }

  @Override
  public Optional<TradeDetail> findTradeViewById(Long id) {
    Optional<TradeDetail> tradeDetail = tradeDetailRepository.findById(id);
    if (tradeDetail.isEmpty()) {
      return tradeDetail;
    } else {
      TradeDetail trade = tradeDetail.get();
      String updateTerm = DateUtil.getMaxDateTerm(trade.getUpdateTerm());
      String bumpUpdateTerm = DateUtil.getMaxDateTerm(trade.getBumpUpdateTerm());

      TradeDetail newTrade = new TradeDetail(
          trade.getTradeId(), trade.getCategoryId(), trade.getCategoryName(), trade.getTitle(), trade.getDescription(),
          trade.getPreferredLocation(), trade.getPrice(), trade.getOfferable(), trade.getStatus(), trade.getHidden(),
          trade.getCreatedAt(), trade.getUpdatedAt(), trade.getBumpUpdatedAt(), updateTerm, bumpUpdateTerm,
          trade.getLikeCounts(), trade.getChatCounts(), trade.getUserId(), trade.getNickname(), trade.getLocation(),
          trade.getMannerScore()
      );
      return Optional.of(newTrade);
    }
  }

  @Override
  public List<TradeImageList> findAllImageById(Long id) {
    return tradeImageListRepository.findAllByTradeId(id);
  }

  @Override
  public Page<TradeList> findAllByUserId(Long userId, Pageable pageable) {
    return tradeListRepository.findAllByUserId(userId, pageable);
  }

  @Override
  @Transactional
  public Trade save(User user, TradeDto tradeDto, Category category) {
    String title = tradeDto.getTitle();
    String description = tradeDto.getDescription();
    String preferredLocation = tradeDto.getPreferredLocation();

    title = SecurityUtil.sanitizeInput(title);
    description = SecurityUtil.sanitizeInput(description);
    preferredLocation = SecurityUtil.sanitizeInput(preferredLocation);

    Trade trade = new Trade();
    trade.setUser(user);
    trade.setCategory(category);
    trade.setTitle(title);
    trade.setDescription(description);
    trade.setPreferredLocation(preferredLocation);
    trade.setPrice(tradeDto.getPrice());
    trade.setOfferable(tradeDto.getOfferable());
    trade.setBumpCount(0);
    trade.setStatus("available");
    trade.setHidden(false);
    trade.setCreatedAt(LocalDateTime.now());
    trade.setUpdatedAt(LocalDateTime.now());

    return tradeRepository.save(trade);
  }

  @Override
  @Transactional
  public Trade edit(Trade trade, TradeEditDto tradeEditDto, Category category) {
    String title = tradeEditDto.getTitle() != null ?
        SecurityUtil.sanitizeInput(tradeEditDto.getTitle()) : null;
    String description = tradeEditDto.getDescription() != null ?
        SecurityUtil.sanitizeInput(tradeEditDto.getDescription()) : null;
    String preferredLocation = tradeEditDto.getPreferredLocation() != null ?
        SecurityUtil.sanitizeInput(tradeEditDto.getPreferredLocation()) : null;

    if (category != null)
      trade.setCategory(category);
    if (title != null && !title.isBlank())
      trade.setTitle(title);
    if (description != null && !description.isBlank())
      trade.setDescription(description);
    if (preferredLocation != null && !preferredLocation.isBlank())
      trade.setPreferredLocation(preferredLocation);
    if (tradeEditDto.getPrice() != null)
      trade.setPrice(tradeEditDto.getPrice());
    if (tradeEditDto.getOfferable() != null
        && tradeEditDto.getOfferable().equals(trade.getOfferable()))
      trade.setOfferable(tradeEditDto.getOfferable());
    if (tradeEditDto.getStatus() != null)
      trade.setStatus(tradeEditDto.getStatus());
    if (tradeEditDto.getHidden() != null
        && tradeEditDto.getHidden().equals(trade.getHidden()))
      trade.setHidden(tradeEditDto.getHidden());

    trade.setUpdatedAt(LocalDateTime.now());

    return tradeRepository.save(trade);
  }

  @Override
  public void delete(Long id) {
    tradeRepository.deleteById(id);
  }

  @Override
  public void confirmTrade(Long tradeId) {
    Trade trade = tradeRepository.findById(tradeId)
        .orElseThrow(() -> new IllegalArgumentException("거래글이 존재하지 않습니다"));

    if ("completed".equalsIgnoreCase(trade.getStatus())) {
      throw new IllegalStateException("이미 완료된 거래입니다");
    }

    trade.setStatus("completed"); // 또는 ENUM 사용 가능
    tradeRepository.save(trade);
  }

}
