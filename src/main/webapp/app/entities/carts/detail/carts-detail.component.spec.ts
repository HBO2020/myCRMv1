import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CartsDetailComponent } from './carts-detail.component';

describe('Carts Management Detail Component', () => {
  let comp: CartsDetailComponent;
  let fixture: ComponentFixture<CartsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CartsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ carts: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CartsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CartsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load carts on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.carts).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
