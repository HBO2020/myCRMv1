import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { LivraisonClService } from '../service/livraison-cl.service';

import { LivraisonClComponent } from './livraison-cl.component';

describe('LivraisonCl Management Component', () => {
  let comp: LivraisonClComponent;
  let fixture: ComponentFixture<LivraisonClComponent>;
  let service: LivraisonClService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [LivraisonClComponent],
    })
      .overrideTemplate(LivraisonClComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LivraisonClComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LivraisonClService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.livraisonCls?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
