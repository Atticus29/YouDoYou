$(() =>
  $('#drawer-toggle').on('click', function() {
    let toggle = $(this).addClass('active').attr('data-toggle');
    $(this).siblings('#drawer-toggle').removeClass('active');
    return $('.surveys').removeClass('grid list').addClass(toggle);
  })
);
